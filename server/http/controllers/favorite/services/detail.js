const favoriteRepo     = require('../../../queries/favorite')

module.exports = async (req, res, next) => {
  let favorite = await favoriteRepo.findOneByUserAndItem(req.profile, req.params.item)

  if (favorite) {
    return res.json({
      code: 200,
      message: 'favorite tersedia',
      favorite: true
    })
  }

  return res.json({
    code: 404,
    message: 'favorite tidak tersedia',
    favorite: false
  })
}
