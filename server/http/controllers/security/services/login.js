const jwt              = require('jsonwebtoken')
const bcrypt           = require('bcrypt')
const models           = require('../../../models')
const { byUsername }   = require('../../../queries/user')
const { errorHandler } = require('../../global')

module.exports = async (req, res, next) => {
  const user = await models.user.findOne(byUsername(req.body.username))
  if (!user) {
    const error = errorHandler(401, 'username/password tidak valid')
    return next(error)
  }

  const isPasswordMatch = await bcrypt.compare(req.body.password, user.password)
  if (isPasswordMatch === false) {
    const error = errorHandler(401, 'username/password tidak valid')
    return next(error)
  }

  const token = jwt.sign(user, process.env.JWT_SECRET, { algorithm: process.env.JWT_ALGORITHM })
  return res.json({
    code: 200,
    message: 'login success',
    token: token
  })
}
