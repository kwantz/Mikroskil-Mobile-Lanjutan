module.exports = {
  // service
  search: require('./services/search'),
  detail: require('./services/detail'),

  // middleware
  searchMiddleware: require('./middlewares/search'),
  detailMiddleware: require('./middlewares/detail')
}
