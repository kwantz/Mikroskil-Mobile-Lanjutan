<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Model;

class Transaksi extends Model
{
    protected $table = 'transaksi';
    
    public function pengguna()
    {
        return $this->hasOne('App\Models\Pengguna', 'id', 'id_pengguna');
    }
}
