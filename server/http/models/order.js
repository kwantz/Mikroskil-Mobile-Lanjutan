/* eslint key-spacing: ["error", { "align": "colon" }] */
module.exports = (sequelize, DataTypes) => {
  return sequelize.define('orders',
    {
      id            : { type: DataTypes.INTEGER, autoIncrement: true, primaryKey: true },
      cart_id       : { type: DataTypes.INTEGER, allowNull: false },
      transaction_id: { type: DataTypes.INTEGER, allowNull: false }
    },
    {
      tableName      : 'orders',
      paranoid       : true,
      timestamps     : true,
      createdAt      : 'created_at',
      updatedAt      : 'updated_at',
      deletedAt      : 'deleted_at',
      freezeTableName: true
    }
  )
}
