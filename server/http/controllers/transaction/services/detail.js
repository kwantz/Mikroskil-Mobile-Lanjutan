const itemRepo              = require('../../../queries/item')
const orderRepo             = require('../../../queries/order')
const transactionRepo       = require('../../../queries/transaction')
const transactionStatusRepo = require('../../../queries/transaction_status')
const { errorHandler }      = require('../../global')

module.exports = async (req, res, next) => {
  let transaction = await transactionRepo.findOneById(req.params.id, req.profile)

  if (!transaction) {
    const error = errorHandler(404, 'tidak ada transaksi')
    return next(error)
  }

  transaction.items = await itemRepo.findByTransaction(transaction.id)
  transaction.total_amount = await orderRepo.findTotalAmount(transaction.id)
  transaction.status = await transactionStatusRepo.findByTransaction(transaction.id)

  return res.json({
    code: 200,
    message: 'favorite tersedia',
    transaction: transaction
  })
}
