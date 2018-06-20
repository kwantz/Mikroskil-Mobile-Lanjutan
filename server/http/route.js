const item     = require('./controllers/item')
const cart     = require('./controllers/cart')
const profile  = require('./controllers/profile')
const category = require('./controllers/category')
const favorite = require('./controllers/favorite')
const security = require('./controllers/security')

module.exports = (app) => {
  // Security Controller
  app.post('/login', security.login)
  app.post('/check', security.check)
  app.post('/register', security.register)

  // Profile Controller
  app.get('/profile', security.jsonwebtokenMiddleware, profile.detail)
  app.post('/profile', security.jsonwebtokenMiddleware, profile.create)

  // Item Controller
  app.get('/item', item.searchMiddleware, item.search)
  app.get('/item/:id', item.detailMiddleware, item.detail)

  // Category Controller
  app.get('/category', category.all)

  // Favorite Controller
  app.get('/favorite', security.jsonwebtokenMiddleware, favorite.all)
  app.get('/favorite/:item', security.jsonwebtokenMiddleware, favorite.detail)
  app.post('/favorite', security.jsonwebtokenMiddleware, favorite.create)
  app.delete('/favorite', security.jsonwebtokenMiddleware, favorite.remove)

  // Cart Controller
  app.get('/cart', security.jsonwebtokenMiddleware, cart.all)
  app.get('/cart/:item', security.jsonwebtokenMiddleware, cart.detail)
  app.put('/cart', security.jsonwebtokenMiddleware, cart.update)
  app.post('/cart', security.jsonwebtokenMiddleware, cart.create)
  app.delete('/cart/:item', security.jsonwebtokenMiddleware, cart.remove)

  // Transaction Controller
  app.get('/transaction', security.jsonwebtokenMiddleware)
  app.post('/transaction', security.jsonwebtokenMiddleware)

  // Notification (Transaction Status) Controller
  app.get('/notification', security.jsonwebtokenMiddleware)

  // Error Handler Middleware
  app.use((err, req, res, next) => {
    res.json({
      code: err.statusCode,
      message: err.message
    })
  })
}
