# Manual de instalação 


# Versão das ferramentas utilizadas

ferramenta | Versão
------------ | -------------
OpenCV Android SDK | openCV-3.2.0
Android Studio | v3.0
Android SDK build-tools | v23
Android SDK platform-tools | v23.0
Android Official NDK | android-ndk-r12b

* **OS:** Windows 10

# Instalando dependências

Primeiramente faça  download da [**versão 3.2 do opencv para android**](https://opencv.org/releases.html) e [**NDK oficial do Android**](https://developer.android.com/ndk/downloads/index.html)!
* Extraia a NDK para um diretorio simples e **sem espaços** como: *C:\work\android-ndk-r12b*
* Extraia o SDK do OpenCV.



### Ajustando os caminhos para o Java e o NDK:

1. Clique direito no **Este computador**, em **Propriedades** clique em **Configurações avançadas do sistema**.
2. Na janela que se abriu clique em **Avançado** e selecione a ultima opção **Variaveis de ambiente**.
3. Procure pela variavel **PATH** e clique em **Editar**
4. Clique em **Novo** e cole o diretório da sua pasta java como: *C:\Program Files\Java\jdk1.8.0_40\bin\*

5. Adicione o caminho do **NDK** como no passo 4! Coloque apenas o diretório raiz como: *C:\work\android-ndk-r12b*
6. Feito!

### Instalando Cmake para o Android SDK:

1. Abra **Android Studio** e clique em **Settings**
2. Va para **Appearance & Behavior** e clique em **System settings** procure por **Android SDK**!
3. Clique na aba **SDK Tools** e tenha certeza que **CMake** está instalado, se não instale-o!
