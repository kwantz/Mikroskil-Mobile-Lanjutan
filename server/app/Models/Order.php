<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Model;

class Order extends Model
{
    protected $table = 'order';

    public function barang()
    {
        return $this->hasOne('App\Models\Barang', 'id', 'id_barang');
    }
    
    public function transaksi()
    {
        return $this->hasOne('App\Models\Transaksi', 'id', 'id_transaksi');
    }
}
