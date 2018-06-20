const userRepo         = require('../../../queries/user')
const profileRepo      = require('../../../queries/profile')
const { errorHandler } = require('../../global')

module.exports = async (req, res, next) => {
  console.log('masuk sini')
  let profile = await profileRepo.findOneByUserId(req.profile)
  let user = await userRepo.findOneById(req.profile)

  if (!profile) {
    const error = errorHandler(400, 'profile tidak ditemukan')
    return next(error)
  }

  profile.email = user.email
  return res.json({
    code: 200,
    message: 'profile tersedia',
    profile: profile
  })
}
