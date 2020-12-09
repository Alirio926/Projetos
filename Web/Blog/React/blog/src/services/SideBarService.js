import {URL_API} from './Base';

export function apiListarPost(callback){
    return fetch(`${URL_API}/api/categoria/listar`).then(resultado => resultado.json(callback));
}