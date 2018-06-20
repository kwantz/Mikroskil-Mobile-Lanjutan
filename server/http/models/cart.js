/* eslint key-spacing: ["error", { "align": "colon" }] */
module.exports = (sequelize, DataTypes) => {
  return sequelize.define('cart',
    {
      id        : { type: DataTypes.INTEGER, autoIncrement: true, primaryKey: true },
      item_id   : { type: DataTypes.INTEGER, allowNull: false },
      profile_id: { type: DataTypes.INTEGER, allowNull: false },
      quantity  : { type: DataTypes.INTEGER, allowNull: false },
      note      : { type: DataTypes.TEXT }
    },
    {
      tableName      : 'cart',
      timestamps     : true,
      createdAt      : 'created_at',
      updatedAt      : 'updated_at',
      freezeTableName: true
    }
  )
}
