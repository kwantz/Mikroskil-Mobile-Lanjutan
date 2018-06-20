const orderRepo        = require('../../../queries/order')
const transactionRepo  = require('../../../queries/transaction')
const { errorHandler } = require('../../global')

module.exports = async (req, res, next) => {
  let transactions = await transactionRepo.findAll(req.profile)

  if (transactions.length === 0) {
    const error = errorHandler(404, 'tidak ada transaksi')
    return next(error)
  }

  for (let i = 0, maxLen = transactions.length; i < maxLen; i++) {
    transactions[i].total_amount = await orderRepo.findTotalAmount(transactions[i].id)
  }

  return res.json({
    code: 200,
    message: 'favorite tersedia',
    transactions: transactions
  })
}
