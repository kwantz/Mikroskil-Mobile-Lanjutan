const bcrypt           = require('bcrypt')
const models           = require('../../../models')
const { byUsername }   = require('../../../queries/user')
const { errorHandler } = require('../../global')

module.exports = async (req, res, next) => {
  const user = await models.user.findOne(byUsername(req.body.username))
  if (user) {
    const error = errorHandler(401, 'username telah terdaftar')
    return next(error)
  }

  const hashPassword = await bcrypt.hash(req.body.password, 10)

  const newUser = {
    email: req.body.email,
    username: req.body.username,
    password: hashPassword
  }

  const status = await models.user.create(newUser)
  if (!status) {
    return res.json({
      code: 500,
      message: 'internal server error'
    })
  }

  return res.json({
    code: 200,
    message: 'registrasi berhasil'
  })
}
