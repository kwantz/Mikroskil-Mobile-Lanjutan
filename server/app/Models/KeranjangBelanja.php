<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Model;

class KeranjangBelanja extends Model
{
    protected $table = 'keranjang_belanja';

    public function barang()
    {
        return $this->hasOne('App\Models\Barang', 'id', 'id_barang');
    }
    
    public function pengguna()
    {
        return $this->hasOne('App\Models\Pengguna', 'id', 'id_pengguna');
    }
}
