/* eslint key-spacing: ["error", { "align": "colon" }] */
module.exports = (sequelize, DataTypes) => {
  return sequelize.define('profile',
    {
      id     : { type: DataTypes.INTEGER, autoIncrement: true, primaryKey: true },
      user_id: { type: DataTypes.INTEGER, allowNull: false, unique: true },
      name   : { type: DataTypes.STRING(100), allowNull: false, unique: true },
      phone  : { type: DataTypes.STRING(12), allowNull: false },
      picture: { type: DataTypes.STRING(255), allowNull: true }
    },
    {
      tableName      : 'profile',
      paranoid       : true,
      timestamps     : true,
      createdAt      : 'created_at',
      updatedAt      : 'updated_at',
      deletedAt      : 'deleted_at',
      freezeTableName: true
    }
  )
}
