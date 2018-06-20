/* eslint key-spacing: ["error", { "align": "colon" }] */
module.exports = (sequelize, DataTypes) => {
  return sequelize.define('transaction_status',
    {
      id            : { type: DataTypes.INTEGER, autoIncrement: true, primaryKey: true },
      transaction_id: { type: DataTypes.INTEGER, allowNull: false },
      title         : { type: DataTypes.STRING(100), allowNull: false },
      description   : { type: DataTypes.TEXT }
    },
    {
      tableName      : 'transaction_status',
      paranoid       : true,
      timestamps     : true,
      createdAt      : 'created_at',
      updatedAt      : 'updated_at',
      deletedAt      : 'deleted_at',
      freezeTableName: true
    }
  )
}
