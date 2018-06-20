const jwt            = require('jsonwebtoken')
const models         = require('../../../models')
const { byUsername } = require('../../../queries/user')

module.exports = async (req, res, next) => {
  const error = {
    code: 401,
    message: 'token tidak valid'
  }
  try {
    const [bearer, token] = req.get('Authorization').split(' ')
    console.log(bearer, token)

    const jwtUser = await jwt.verify(token, process.env.JWT_SECRET)

    const userQuery = byUsername(jwtUser.username)
    const user = await models.user.findOne(userQuery)

    if (jwtUser.id !== user.id || jwtUser.password !== user.password) {
      return res.json(error)
    }

    req.profile = user.id
    return next()
  } catch (err) {
    return res.json(error)
  }
}
