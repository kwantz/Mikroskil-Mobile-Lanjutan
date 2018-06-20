module.exports = (req, res, next) => {
  if (req.params.id) return next()

  return res.json({
    code: 400,
    message: 'data tidak valid, parameter tidak boleh kosong'
  })
}
