import React, { Component } from "react";
// Classe de serviços
import {apiListarPost, abbreviate, options} from './../services/PostService';
// Parser HTML tag do post vindas do ReactSummernote
import ReactHtmlParser from 'react-html-parser';


import logo from '././../imagens/306.jpg';
import './MainPost.css';


// Falta adicionar o perfil do usuario logado, a afim de saber se ele é autor
// adim ou anomynous, e assim, fazer apparecer os botôes de acordo linha 71 e 104
class MainPost extends Component{
    
    constructor(props){
        super(props);
        this.state = {
            postagens : [{
                id              : 0,
                titulo          : " ",
                texto           : " ",
                permalink       : " ",
                dataPostagem    :[
                    2020,
                    3,
                    29,
                    12,
                    19,
                    15
                ],
                autorId         : 1,
                autorNome       : " ",
                autorBiografia  : " ",
                categorias      : [{
                    id          : 1,
                    descricao   : " ",
                    permalink   : " "
                }],
            }]
        }
    }
    componentDidMount(){
        apiListarPost().then(dados => this.setState(dados));
    }
    
    render(){
        function UserAdmin(props){
            return(
                <div>
                    <a className="btn btn-primary mx-1" href={"/post/" + props.permalink}>Ler mais&rarr;</a>
                    <a className="btn btn-primary mx-1" href={"/deletar/" + props.permalink}>Apagar Post&rarr;</a>
                </div>
            )
        }
        function UserAutor(props){
            return(
                <div>                   
                    <a className="btn btn-primary mx-1" href={"/post/" + props.permalink}>Ler mais&rarr;</a>
                    <a className="btn btn-primary mx-1" href={"/newpost/" + props.permalink}>Editar Post&rarr;</a>
                </div>
            )
        }
        function UserAnonymous(props){
            return(
                <div>                   
                    <a className="btn btn-primary mx-1" href={"/post/" + props.permalink}>Ler mais&rarr;</a>
                </div>
            )
        }
        function UserPageControl(props){
            const perfil = "AUTOR";//props.perfil;
            const perma = props.permalink;
            if(perfil === "ADMIN"){
                console.log(perfil+" "+perma)
                return <UserAdmin permalink={perma}/>
            }
            if(perfil === "AUTOR"){
                console.log(perfil+" "+perma)
                return <UserAutor permalink={perma}/>
            }
            console.log(perfil+" "+perma)
            return <UserAnonymous permalink={perma}/>
        }
        return(
                <div>
                    <div><br></br>
                    </div>
                    <h1 className="my-4"><u>Alirio Oliveira</u> - <small>Eletrônica | Programação</small></h1>
                    <div>
                        {
                        this.state.postagens.map((post, indice)=>{
                        return (
                        <div className="card shadow-sm bg-white rounded div-Post">
                            {/*<img class="card-img-top img-rounded" src={logo} alt="Card image cap" width="90" height="150%" class="img-rounded" ></img>*/}
                            <div className="card-body" >
                                <a href={"/post/" + post.permalink} ><h2 className="card-title">{post.titulo}</h2></a>
                                <div className="div-text">
                                    { 
                                    ReactHtmlParser(
                                        abbreviate(post.texto, 150), options) 
                                    }

                                    {/*UserPageControl(post)*/}
                                </div>                         
                            </div>
                            <div className="card-footer text-muted">
                                Postado em {post.dataPostagem} por 
                                <a href={"/autor/" + post.autorId}> {post.autorNome}.  </a>
                                <label className="ic-w mr-1">Categoria(s):  
                                    {
                                    post.categorias.map((trash, index)=>{
                                        return(
                                            <a href={"/categoria/" + post.categorias[index].permalink} className="badge badge-pill badge-primary ic-w mr-1">{post.categorias[index].descricao}</a> 
                                        )
                                    })
                                    }                                            
                                </label>                                                                              
                            </div>
                        </div>                                                            
                        )
                        })
                        }
                    </div>
                    <br></br>
                    <ul className="pagination justify-content-center mb-4">
                        <li className="page-item">
                            <a className="page-link" href="#">&larr; Mais antigo</a>
                        </li>
                            <li className="page-item disabled">
                            <a className="page-link" href="#">Mais novo &rarr;</a>
                        </li>
                    </ul>
                </div>
        )
    }
}

export default MainPost;