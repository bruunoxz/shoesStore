import React from "react";
import {StyleSheet, TouchableOpacity } from 'react-native';

import Texto from "./Texto"

export default function Botao({textoBotao, clickBotao}) {
    return <TouchableOpacity style={styles.botao} onPress={clickBotao}>
        <Texto style={styles.botaoTexto}>{textoBotao}</Texto>
    </TouchableOpacity>
}

const styles = StyleSheet.create({
    botao: {
        width: "80%",
        marginTop: 16,
        backgroundColor: "black",
        paddingVertical: 16,
        borderRadius: 6,
    },
    botaoTexto: {
        textAlign: "center",
        fontSize: 22,
        lineHeight: 26,
        fontWeight: "bold",
        color: "white"
    }
});