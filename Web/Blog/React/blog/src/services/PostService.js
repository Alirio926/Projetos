import {URL_API} from './Base';

export function newPost(data){
    fetch(URL_API + "/post/newpost", {
        method: 'POST',
        body: data,
    });
}
export function apiListarPost(callback){
    return fetch(`${URL_API}/api/post/listar`).then(resultado => resultado.json(callback));
}

export function apiNewComment(data){
  fetch(URL_API + "/api/comentario", {
    method: 'POST',
    body: data,
});
}
export function abbreviate(str, max, suffix)
{
    if((str = str.replace(/^\s+|\s+$/g, '').replace(/[\r\n]*\s*[\r\n]+/g, ' ').replace(/[ \t]+/g, ' ')).length <= max)
    {
      return str;
    }
    
    var 
    abbr = '',
    str = str.split(' '),
    suffix = (typeof suffix !== 'undefined' ? suffix : ' ...'),
    max = (max - suffix.length);
    
    for(var len = str.length, i = 0; i < len; i ++)
    {
      if((abbr + str[i]).length < max)
      {
        abbr += str[i] + ' ';
      }
      else { break; }
    }

    return abbr.replace(/[ ]$/g, '') + suffix;
}

export function transform(node, index) {
    // do not render any <span> tags
    if (node.type === 'tag' && node.name === 'img') {
        console.log(node.name);
      return null;
    }
  }
export const options = {
    decodeEntities: true,
    transform
  };