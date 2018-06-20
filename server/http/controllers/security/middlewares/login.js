module.exports = async (req, res, next) => {
  const REGEX_USERNAME = new RegExp('^[a-zA-Z0-9_]+$')
  let status = true
  let error = {
    code: 400,
    message: 'data tidak valid',
    fields: {}
  }

  if (req.body.username.length < 1 || req.body.username.length > 20) {
    error.fields.username = 'panjang username min. 1 & max. 20'
    status = false
  }

  if (req.body.password.length < 5 || req.body.password.length > 12) {
    error.fields.password = 'panjang password min. 5 & max. 12'
    status = false
  }

  if (!REGEX_USERNAME.test(req.body.username)) {
    error.fields.username = 'username hanya boleh angka, huruf, dan _'
    status = false
  }

  if (status) return next()

  return res.json(error)
}
