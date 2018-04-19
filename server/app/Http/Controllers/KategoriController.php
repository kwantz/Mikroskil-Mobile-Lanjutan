<?php

namespace App\Http\Controllers;

use Illuminate\Http\Request;
use Illuminate\Routing\Controller;
use App\Models\Kategori;

class KategoriController extends Controller
{
    /**
     * Retrieve the kategori for the given ID.
     *
     * @param  int  $id
     * @return Response
     */
    public function show($id)
    {
        return Kategori::findOrFail($id);
    }

    /**
     * Store a new kategori.
     *
     * @param  Request  $request
     * @return Response
     */
    public function store(Request $request)
    {
        $kategori = new Kategori();
        $kategori->nama = $request->input('nama');
        $kategori->icon = $request->file('icon');
        $kategori->save();
    }
    
}