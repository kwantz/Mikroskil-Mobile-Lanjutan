<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Model;

class Favorit extends Model
{
    protected $table = 'favorit';
    
    public function pengguna()
    {
        return $this->hasOne('App\Models\Pengguna', 'id', 'id_pengguna');
    }

    public function barang()
    {
        return $this->hasOne('App\Models\Barang', 'id', 'id_barang');
    }
}
