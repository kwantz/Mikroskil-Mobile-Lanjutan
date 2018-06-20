/* eslint key-spacing: ["error", { "align": "colon" }] */
module.exports = (sequelize, DataTypes) => {
  return sequelize.define('user',
    {
      id      : { type: DataTypes.INTEGER, autoIncrement: true, primaryKey: true },
      email   : { type: DataTypes.STRING(100), allowNull: false, unique: true },
      username: { type: DataTypes.STRING(20), allowNull: false, unique: true },
      password: { type: DataTypes.STRING(255), allowNull: false }
    },
    {
      tableName      : 'user',
      paranoid       : true,
      timestamps     : true,
      createdAt      : 'created_at',
      updatedAt      : 'updated_at',
      deletedAt      : 'deleted_at',
      freezeTableName: true
    }
  )
}
