const jwt            = require('jsonwebtoken')
const models         = require('../../../models')
const { byUsername } = require('../../../queries/user')

module.exports = async (req, res, next) => {
  const error = {
    code: 401,
    message: 'token tidak valid'
  }

  try {
    const jwtUser = await jwt.verify(req.body.token, process.env.JWT_SECRET)

    const userQuery = byUsername(jwtUser.username)
    const user = await models.user.findOne(userQuery)

    if (jwtUser.id !== user.id || jwtUser.password !== user.password) {
      req.profile = user.id
      return res.json(error)
    }

    return res.json({
      code: 200,
      message: 'Token valid'
    })
  } catch (err) {
    return res.json(error)
  }
}
