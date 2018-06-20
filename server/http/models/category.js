/* eslint key-spacing: ["error", { "align": "colon" }] */
module.exports = (sequelize, DataTypes) => {
  return sequelize.define('category',
    {
      id  : { type: DataTypes.INTEGER, autoIncrement: true, primaryKey: true },
      name: { type: DataTypes.STRING(100), allowNull: false },
      icon: { type: DataTypes.STRING(255), allowNull: false }
    },
    {
      tableName      : 'category',
      paranoid       : true,
      timestamps     : true,
      createdAt      : 'created_at',
      updatedAt      : 'updated_at',
      deletedAt      : 'deleted_at',
      freezeTableName: true
    }
  )
}
