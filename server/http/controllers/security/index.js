module.exports = {
  // service
  check: require('./services/check'),
  login: require('./services/login'),
  register: require('./services/register'),

  // middleware
  loginMiddleware: require('./middlewares/login'),
  jsonwebtokenMiddleware: require('./middlewares/jsonwebtoken')
}
