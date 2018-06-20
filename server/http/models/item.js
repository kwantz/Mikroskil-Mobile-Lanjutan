/* eslint key-spacing: ["error", { "align": "colon" }] */
module.exports = (sequelize, DataTypes) => {
  return sequelize.define('item',
    {
      id         : { type: DataTypes.INTEGER, autoIncrement: true, primaryKey: true },
      category_id: { type: DataTypes.INTEGER, allowNull: false },
      name       : { type: DataTypes.STRING(100), allowNull: false },
      description: { type: DataTypes.TEXT, allowNull: false },
      picture    : { type: DataTypes.STRING(255), allowNull: false },
      seen       : { type: DataTypes.INTEGER, allowNull: false },
      stock      : { type: DataTypes.INTEGER, allowNull: false },
      price      : { type: DataTypes.INTEGER, allowNull: false },
      discount   : { type: DataTypes.INTEGER }
    },
    {
      tableName      : 'item',
      paranoid       : true,
      timestamps     : true,
      createdAt      : 'created_at',
      updatedAt      : 'updated_at',
      deletedAt      : 'deleted_at',
      freezeTableName: true
    }
  )
}
