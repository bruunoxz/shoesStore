import Logo from "../../assets/Logo.png";
import MeiaBranca from "../../assets/meiaAdidasBranca.png";
import Cadarço from "../../assets/cadarçoPreto.jpg";
import MeiaPreta from "../../assets/meiaAdidasPreta.png";

const produto = {
    topo: {
        titulo: "Detalhes do produto",
    },
    detalhes: {
        logo: Logo,
        nome: "Adidas Campus 00s Black",
        detalhe: "Depois de conquistar as quadras, o tênis adidas Campus não demorou muito para ser adotado fora delas também. Esta versão transforma o modelo icônico original com materiais, cores e proporções modernos. Este tênis possui cabedal em couro premium forrado com moletinho macio, tudo isso sobre uma entressola off-white, uma conexão clara com o legado do Campus",
        preco: "R$ 699,99",
        botao: "Adicionar ao Carrinho"
    },
    itens:{
        titulo: "Kit de Brinde",
        lista: [
            {
                nome: "Par de Meias Brancas Cano Alto Adidas",
                imagem: MeiaBranca
            },
            {
                nome: "Cadarço Preto",
                imagem: Cadarço
            },
            {
                nome: "Par de Meias Pretas Cano Alto Adidas",
                imagem: MeiaPreta
            }
        ]
    }
}
export default produto;