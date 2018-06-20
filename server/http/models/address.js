/* eslint key-spacing: ["error", { "align": "colon" }] */
module.exports = (sequelize, DataTypes) => {
  return sequelize.define('address',
    {
      id        : { type: DataTypes.INTEGER, autoIncrement: true, primaryKey: true },
      city_id   : { type: DataTypes.INTEGER, allowNull: false },
      profile_id: { type: DataTypes.INTEGER, allowNull: false },
      name      : { type: DataTypes.STRING(100), allowNull: false },
      postcode  : { type: DataTypes.STRING(10), allowNull: false },
      latitude  : { type: DataTypes.DECIMAL },
      longitude : { type: DataTypes.DECIMAL }
    },
    {
      tableName      : 'address',
      paranoid       : true,
      timestamps     : true,
      createdAt      : 'created_at',
      updatedAt      : 'updated_at',
      deletedAt      : 'deleted_at',
      freezeTableName: true
    }
  )
}
