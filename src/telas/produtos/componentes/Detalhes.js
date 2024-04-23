import React from 'react';
import { StyleSheet, Image, View, TouchableOpacity, Alert } from 'react-native';

import Texto from "../../../componentes/Texto"
import Botao from "../../../componentes/Botao" 

//import uma fonte diferente do Google Fonts
//npm expo install expo-font @expo-google-fonts/nome-da-fonte
export default function Detalhes({ logo, nome, detalhe, preco, botao }) {
    return <View style={styles.produto}>
        <View style={styles.logotipo}>
            <Image source={logo} style={styles.logo} resizeMode='contain'></Image>
            <Texto style={styles.nome}>{nome}</Texto>
        </View>
        <Texto style={styles.descricao}> {detalhe} </Texto>
        <Texto style={styles.preco}>{preco}</Texto>
        <Botao textoBotao={botao} clickBotao={()=>{Alert.alert("Em breve!","Estamos preparando uma nova função para você")}}/>
    </View>
}

const styles = StyleSheet.create({
    produto: {
      paddingVertical: 8,
      paddingHorizontal: 16,
    },
    nome: {
      color: "black",
      fontWeight: "bold",
      fontSize: 24,
      paddingTop: 25,
      paddingLeft: 25
    },
    descricao: {
      fontSize: 18,
    },
    preco: {
      fontSize: 26,
      fontWeight: "bold",
      marginTop: 8
    },
    logo: {
      width: 70,
      height: 70,
    },
    logotipo: {
      flexDirection: "row",
    }
  });