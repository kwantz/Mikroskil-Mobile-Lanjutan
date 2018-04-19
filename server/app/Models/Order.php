<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Model;

class Order extends Model
{
    protected $table = 'order';

    public function keranjang()
    {
        return $this->hasOne('App\Models\KeranjangBelanja', 'id', 'id_keranjang');
    }
    
    public function transaksi()
    {
        return $this->hasOne('App\Models\Transaksi', 'id', 'id_transaksi');
    }
}
