import React, { Component } from 'react';
import { Redirect } from 'react-router-dom';

import './MainPost.css';

import logo from './../imagens/306.jpg';

import {loadPost, apiNewComment} from './../services/PostService';
import {URL_API} from './../services/Base';
import ReactHtmlParser from 'react-html-parser';

import * as $ from './../include/jquery';
import ReactSummernote from 'react-summernote';
import 'react-summernote/dist/react-summernote.css'; // import styles
import 'react-summernote/lang/summernote-pt-BR'; // you can import any other locale

class ShowPost extends Component{   
    state = {
        redirect: false
    }
    setRedirect() {
        this.setState({
            redirect: true
        })
    }
    renderRedirect() {
        if (this.state.redirect) {
            return <Redirect to={"/post/" + this.state.postagem.permalink}/>
        }
    }
    constructor(props) {
        super(props);
        this.state = {
            postagem : {
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
                comentario      :[{
                    id          : 0,
                    texto       : " ",
                    dataComentario  :[
                        2020,
                        3,
                        29,
                        12,
                        19,
                        15
                    ],  
                    usuario     : {
                        id      : 0,
                        nome    : " ",
                        email   : " ",
                        dataCadastro    : " ",
                        perfil  : " ",
                        avatar  : " ",
                    },                  
                }],
            },
            richText: "",
            value_default: null,
            redirect: false
        }
        
    }
    onChange = (content) => {
        this.setState({
          richText: content          
        })
    }
    componentDidMount(){
        const { handle } = this.props.match.params;
        const permalink_url = URL_API + "/api/post/" + handle;

        fetch(`${permalink_url}`)
            .then((resultado) => resultado.json()).then(resultado => this.setState(resultado))

        console.log(permalink_url);
    }
    handleSubmit(event){
        if(!event.target.checkValidity()){
            return;
        }
        event.preventDefault();

        const data = new FormData(event.target);      

        console.log(data);

        apiNewComment(data);        
        
        //this.setRedirect();        
    }
    render(){
        return(
            <div>
                {this.renderRedirect()}
                <div><br></br></div>
                
                <h1 className="my-4"><b>{this.state.postagem.titulo}</b></h1>
                <p className="lead">Por 
                    <a href="#"> {this.state.postagem.autorNome}</a>
                </p>
                <hr></hr>{/*<!-- Date/Time -->*/}

                <p>Publicado: {this.state.postagem.dataPostagem}</p>
                <hr></hr>{/*<!-- Preview Image -->*/}

                <img className="img-fluid rounded" src={logo} alt=""></img>

                <hr>{/*<!-- Post Content -->*/}</hr>

                <p>{
                     ReactHtmlParser(this.state.postagem.texto) 
                }</p>

                <blockquote className="blockquote">
                <p className="mb-0">
                {
                     ReactHtmlParser(this.state.postagem.texto) 
                }
                </p>
                <footer className="blockquote-footer">Citar alguém? 
                    <cite title="Source Title"> {this.state.postagem.autorNome}</cite>
                </footer>
                </blockquote>

                <hr>{/*<!-- Comments Form -->*/}</hr>
                <div className="card my-4">
                    <h5 className="card-header">Deixe um comentário:</h5>
                    <div className="card-body">
                        <form className="was-validated" onSubmit={this.handleSubmit}>
                            <div className="form-group">
                                <input type="hidden" value={this.state.postagem.permalink} name="permalink" classname="is-valid" ></input>
                                <textarea id="text-hidden" name="texto" className="form-control is-invalid" value={this.state.richText} hidden required></textarea>
                                <ReactSummernote
                                        id="summer"
                                        value={this.state.value_default}
                                        options={{
                                        lang: 'pt-BR',
                                        height: 280,
                                        dialogsInBody: true,
                                        toolbar: [
                                            ['style', ['style']],
                                            ['font', ['bold', 'underline', 'clear']],
                                            ['fontname', ['fontname']],
                                            ['para', ['ul', 'ol', 'paragraph']],
                                            ['table', ['table']],
                                            ['insert', ['link', 'picture', 'video']],
                                            ['view', ['fullscreen', 'codeview']]
                                        ]
                                        }}onChange={this.onChange}
                                        shouldComponentUpdate={() => {}}
                                        />
                            </div>
                            <button type="submit" className="btn btn-primary">Publicar</button>
                        </form>
                    </div>
                </div>

                {/*<!-- Single Comment -->*/}
                {
                this.state.postagem.comentario.map((comment, indice) => {
                    return(
                        <div className="media mb-4">
                            <img className="d-flex mr-3 rounded-circle" src={"http://localhost:8080/avatar/load/" + comment.usuario.avatar.id} width="50px" height="50px" alt="" />
                            <div className="media-body">
                                <h5 className="mt-0">{comment.usuario.nome}</h5>
                                {ReactHtmlParser(comment.texto) }
                            </div>
                        </div>
                    )
                })
                }
                

                {/*<!-- Comment with nested comments -->*/}
                <div className="media mb-4">
                    <img className="d-flex mr-3 rounded-circle" src="http://placehold.it/50x50" alt="" />
                    <div className="media-body">
                        <h5 className="mt-0">Commenter Name</h5>
                        Cras sit amet nibh libero, in gravida nulla. Nulla vel metus scelerisque ante sollicitudin. Cras purus odio, vestibulum in vulputate at, tempus viverra turpis. Fusce condimentum nunc ac nisi vulputate fringilla. Donec lacinia congue felis in faucibus.

                        <div className="media mt-4">
                            <img className="d-flex mr-3 rounded-circle" src="http://placehold.it/50x50" alt="" />
                            <div className="media-body">
                                <h5 className="mt-0">Commenter Name</h5>
                                Cras sit amet nibh libero, in gravida nulla. Nulla vel metus scelerisque ante sollicitudin. Cras purus odio, vestibulum in vulputate at, tempus viverra turpis. Fusce condimentum nunc ac nisi vulputate fringilla. Donec lacinia congue felis in faucibus.
                            </div>
                        </div>

                        <div className="media mt-4">
                            <img className="d-flex mr-3 rounded-circle" src="http://placehold.it/50x50" alt="" />
                            <div className="media-body">
                                <h5 className="mt-0">Commenter Name</h5>
                                Cras sit amet nibh libero, in gravida nulla. Nulla vel metus scelerisque ante sollicitudin. Cras purus odio, vestibulum in vulputate at, tempus viverra turpis. Fusce condimentum nunc ac nisi vulputate fringilla. Donec lacinia congue felis in faucibus.
                            </div>
                        </div>

                    </div>
                </div>

            </div>
        )
    }
}
export default ShowPost;