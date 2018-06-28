const item     = require('./controllers/item')
const cart     = require('./controllers/cart')
const profile  = require('./controllers/profile')
const category = require('./controllers/category')
const favorite = require('./controllers/favorite')
const security = require('./controllers/security')

const itemRepo = require('./queries/item')
const cartRepo = require('./queries/cart')
const addressRepo = require('./queries/address')
const profileRepo = require('./queries/profile')
const transactionRepo = require('./queries/transaction')
const models = require('./models')

module.exports = (app) => {
  // Security Controller
  app.post('/login', security.login)
  app.post('/check', security.check)
  app.post('/register', security.register)

  // Profile Controller
  app.get('/profile', security.jsonwebtokenMiddleware, profile.detail)
  app.post('/profile', security.jsonwebtokenMiddleware, profile.create)
  app.get('/profile/address/:id', security.jsonwebtokenMiddleware, async (req, res, next) => {
    let profile = await profileRepo.findOneByUserId(req.profile)
    const updateProfile = await models.profile.update({
      current_address: req.params.id
    }, {
      where: {
        id: profile.id
      }
    })
    return res.json({
      code: 200,
      message: 'OK',
      results: updateProfile
    })
  })

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
  app.get('/transaction', security.jsonwebtokenMiddleware, async (req, res, next) => {
    let transaction = await transactionRepo.findAll(req.profile)

    for (let i = 0, len = transaction.length; i < len; i++) {
      const cart = await cartRepo.findByTransaction(transaction[i].id)
      const date = new Date(transaction[i].created_at)
      const year = date.getFullYear()
      const month = date.getMonth() + 1
      const day = date.getDate()
      const hour = date.getHours()
      const minute = date.getMinutes()
      const second = date.getSeconds()

      transaction[i].date = `${day}/${month}/${year} ${hour}:${minute}:${second}`
      transaction[i].total = 0

      for (let j = 0, maxLen = cart.length; j < maxLen; j++) {
        const item = await itemRepo.findOneById(cart[j].item_id)
        transaction[i].total += (cart[j].quantity * item.price)
      }
    }

    return res.json({
      code: 200,
      message: 'OK',
      results: transaction
    })
  })

  app.get('/transaction/:id', security.jsonwebtokenMiddleware, async (req, res, next) => {
    let transaction = await transactionRepo.findOneById(req.params.id)
    let profile = await profileRepo.findOneByUserId(req.profile)
    let addresses = await addressRepo.findOneById(req.profile, profile.current_address)
    let cart = await cartRepo.findByTransaction(transaction.id)

    addresses.user_name = profile.name
    addresses.user_phone = profile.phone
    transaction.address = addresses
    transaction.cart = cart

    for (let i = 0, maxLen = cart.length; i < maxLen; i++) {
      const item = await itemRepo.findOneById(cart[i].item_id)

      transaction.cart[i].item = item
      transaction.cart[i].item_id = undefined
    }

    return res.json({
      code: 200,
      message: 'OK',
      results: transaction
    })
  })

  app.post('/transaction', security.jsonwebtokenMiddleware, async (req, res, next) => {
    try {
      var d = new Date()
      const newTransaction = await models.transaction.create({
        profile_id: req.profile,
        invoice: `${d.getDate()}${d.getDay()}${d.getFullYear()}${d.getMonth()}${d.getHours()}${d.getMinutes()}${d.getSeconds()}`,
        receipt_number: `${d.getDate()}${d.getDay()}${d.getFullYear()}${d.getMonth()}${d.getHours()}${d.getMinutes()}${d.getSeconds()}`,
        virtual_account: '021-779483138'
      })

      let cart = await cartRepo.findAll(req.profile)

      for (let i = 0, maxLen = cart.length; i < maxLen; i++) {
        await models.orders.create({
          cart_id: cart[i].id,
          transaction_id: newTransaction.id
        })
      }

      return res.json({
        code: 200,
        message: 'OK',
        results: newTransaction
      })
    } catch (err) {
      console.log(err)
    }
  })

  // Address Controller
  app.get('/address', security.jsonwebtokenMiddleware, async (req, res, next) => {
    let addresses = await addressRepo.findAll(req.profile)
    let profile = await profileRepo.findOneByUserId(req.profile)

    for (let i = 0, len = addresses.length; i < len; i++) {
      addresses[i].user_name = profile.name
      addresses[i].user_phone = profile.phone
    }

    return res.json({
      code: 200,
      message: 'OK',
      results: addresses
    })
  })

  app.get('/address/:id', security.jsonwebtokenMiddleware, async (req, res, next) => {
    let addresses = await addressRepo.findOneById(req.profile, req.params.id)
    let profile = await profileRepo.findOneByUserId(req.profile)

    addresses.user_name = profile.name
    addresses.user_phone = profile.phone
    return res.json({
      code: 200,
      message: 'OK',
      results: addresses
    })
  })

  app.post('/address', security.jsonwebtokenMiddleware, async (req, res, next) => {
    const newAddress = await models.address.create({
      profile_id: req.profile,
      city: req.body.city,
      name: req.body.name,
      postcode: req.body.postcode
    })
    return res.json({
      code: 200,
      message: 'OK',
      results: newAddress
    })
  })

  // Error Handler Middleware
  app.use((err, req, res, next) => {
    res.json({
      code: err.statusCode,
      message: err.message
    })
  })
}
