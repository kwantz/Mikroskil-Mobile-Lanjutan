module.exports = {
  // service
  all: require('./services/all'),
  create: require('./services/create'),
  remove: require('./services/remove'),
  detail: require('./services/detail'),

  // middleware
  createMiddleware: require('./middlewares/create')
}
