const models           = require('../../../models')
const { errorHandler } = require('../../global')

module.exports = async (req, res, next) => {
  console.log('kok masuk disini')
  const newProfile = await models.profile.create({
    user_id: req.profile,
    name: req.body.name,
    phone: req.body.number,
    picture: '-'
  })

  if (!newProfile) {
    const error = errorHandler(500, 'internal server error')
    return next(error)
  }

  return res.json({
    code: 200,
    message: 'profile berhasil ditambah',
    profile: newProfile
  })
}
