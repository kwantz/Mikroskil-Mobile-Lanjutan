<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Model;

class Pembayaran extends Model
{
    protected $table = 'pembayaran';

    public function transaksi()
    {
        return $this->hasOne('App\Models\Transaksi', 'id', 'id_transaksi');
    }
    
}
