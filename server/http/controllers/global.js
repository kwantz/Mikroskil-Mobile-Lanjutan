module.exports = {
  errorHandler (statusCode, message) {
    let err = new Error(message)

    err.statusCode = statusCode
    return err
  }
}
