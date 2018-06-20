const categoryRepo = require('../../../queries/category')

module.exports = async (req, res, next) => {
  let categories = await categoryRepo.findAll()

  console.log(categories)
  return res.json({
    code: 200,
    message: 'kategory tersedia',
    categories: categories
  })
}
