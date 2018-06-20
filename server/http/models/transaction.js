/* eslint key-spacing: ["error", { "align": "colon" }] */
module.exports = (sequelize, DataTypes) => {
  return sequelize.define('transaction',
    {
      id             : { type: DataTypes.INTEGER, autoIncrement: true, primaryKey: true },
      profile_id     : { type: DataTypes.INTEGER, allowNull: false },
      invoice        : { type: DataTypes.STRING(50), allowNull: false },
      receipt_number : { type: DataTypes.STRING(50) },
      virtual_account: { type: DataTypes.STRING(50) },
      payment_status : { type: DataTypes.ENUM('pending', 'tolak', 'terima'), defaultValue: 'pending', allowNull: false }
    },
    {
      tableName      : 'transaction',
      paranoid       : true,
      timestamps     : true,
      createdAt      : 'created_at',
      updatedAt      : 'updated_at',
      deletedAt      : 'deleted_at',
      freezeTableName: true
    }
  )
}
