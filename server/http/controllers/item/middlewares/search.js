module.exports = (req, res, next) => {
  if (req.query.title || req.query.search || req.query.category) return next()

  return res.json({
    code: 400,
    message: 'data tidak valid, parameter tidak boleh kosong'
  })
}
