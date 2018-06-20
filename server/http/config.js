module.exports = {
  port: process.env.PORT || 3000,
  database: {
    name: process.env.DB_NAME || 'test',
    user: process.env.DB_USER || 'root',
    pass: process.env.DB_PASS || 'password',
    options: {
      host: process.env.DB_HOST || 'localhost',
      dialect: process.env.DB_DIALECT || 'mysql',
      timezone: process.env.DB_TIMEZONE || 'Asia/Jakarta'
    }
  }
}
