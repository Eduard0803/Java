# 📱 OCR Benchmark App

Aplicativo Android desenvolvido para **avaliar o desempenho de OCR (Reconhecimento Óptico de Caracteres)** em dispositivos móveis. O app mede o tempo de processamento de imagens utilizando reconhecimento de texto e armazena os resultados para análise.

---

## 🚀 Objetivo

O projeto tem como finalidade:

* Medir o **tempo médio de execução do OCR**
* Comparar desempenho entre diferentes execuções
* Registrar resultados localmente para análise posterior

---

## 🛠️ Tecnologias Utilizadas

* **Java (Android)**
* **Android SDK**
* **ML Kit (Text Recognition)** – [DOC](https://developers.google.com/ml-kit/vision/text-recognition)
* **Room Database** – persistência de dados local
* **WorkManager** – execução de tarefas em background
* **Camera API** – captura de imagens
* **Gradle** – gerenciamento de build

---

## 📷 Funcionalidades

* Captura de imagens via câmera
* Processamento de texto com OCR
* Medição do tempo de execução
* Armazenamento dos resultados localmente
* Execução de testes em background

---

## 📊 Como funciona

1. O usuário captura ou fornece uma imagem
2. O app executa o OCR usando ML Kit
3. O tempo de processamento é medido
4. O resultado é salvo no banco local (Room)
5. Os dados podem ser usados para análise de performance

---

## 📦 Requisitos

* Android Studio instalado
* Dispositivo com Android 10+ (ou conforme configurado no projeto)
* Permissão de câmera habilitada

---

## 📌 Observações

* O desempenho pode variar dependendo do dispositivo
* Ideal para testes comparativos e experimentos com OCR
