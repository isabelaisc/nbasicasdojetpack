package com.example.basicojetpackcompose

//importações
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons.Filled
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.codelab.basics.ui.theme.BasicsCodelabTheme

// inicia quando app for aberto
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        //definição do layout, diferente do uso do arquivo xml, chama funções de composição
        super.onCreate(savedInstanceState)
        setContent {
            maneira de definir o estilo de composição
            BasicoJetpackComposeTheme {
                modificadores informam como serão dispostos, exibidos ou se comportarão no layout pai os elemento da IU
                MyApp(modifier = Modifier.fillMaxSize())
            }
        }
    }
}

// funções de composição podem ser usadas como qualquer outra função 
@Composable
fun MyApp(modifier: Modifier = Modifier) {

    //está usando uma palavra-chave by em vez de =. É um delegado de propriedade que evita que você digite .value todas as vezes
    var shouldShowOnboarding by rememberSaveable { mutableStateOf(true) }

   // renderizando a cor do segundo plano
    Surface(modifier, color = MaterialTheme.colorScheme.background) {
        if (shouldShowOnboarding) {
            // estado que criamos em OnboardingScreen com a composição MyApp
            // uma expressão lambda vazia significa "não fazer nada", o que é perfeito para uma visualização
            // quando o botão é clicado, shouldShowOnboarding é definido como false
            OnboardingScreen(onContinueClicked = { shouldShowOnboarding = false })
        } else {
            // função que produzirá uma parte da hierarquia de IUs que exibe a entrada fornecida
            Greetings()
        }
    }
}

// Função OnboardingScreen (tela integrada - tela inicial)
@Composable
fun OnboardingScreen(
    onContinueClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    // como estarão os elementos da IU
    Column(
        //alinhamento 
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Texto á ser apresentado na tela inicial
        Text("Welcome to the Basics Codelab!")
        Button(
            modifier = Modifier.padding(vertical = 24.dp),
            //muda o estado do botão, acionando a função on onContinueClicked
            onClick = onContinueClicked
        ) {
            // Texto á ser apresentado no botaõ da tela inicial
            Text("Continue")
        }
    }
}


// Função Greetings01
@Composable
private fun Greetings(
    modifier: Modifier = Modifier,
    // construtor de lista que permite definir o tamanho e preenchê-la com o valor contido na lambda ( $it representa o índice da lista)
    names: List<String> = List(1000) { "$it" }
) {
    // exibe uma coluna rolável e renderiza somente os itens visíveis na tela, permitindo ganhos de desempenho ao renderizar uma lista grande
    LazyColumn(modifier = modifier.padding(vertical = 4.dp)) {
        items(items = names) { name ->
            Greeting(name = name)
            //hierarquizando os itens e names
        }
    }
}

// Função Greetings02
@Composable
private fun Greetings(name: String) {
    Card(
        // mudando as cores dos cards
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primary
        ),
        // indicando como será disposto os cards 
        modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp)
    )  {
         CardContent(name)
    }
}

// Função CardContent (conteúdo do cartão)
@Composable
private fun CardContent(name: String) {

    // expanded - armazenando um valor que indique se cada item está aberto ou não, estado do item
    // remember - protegendo contra a recomposição, para que o estado não seja redefinido
    // função mutableStateOf - adiciona um estado interno a uma composição, que faz com que o Compose recomponha funções que leiam esse State
    val expanded by remember { mutableStateOf(false) }

    // Linha
    Row(
        // indicando como é disposto
        modifier = Modifier
            // tamanho do espaço ao redor do conteúdo ou card
            .padding(12.dp)
            // automatizará o processo de criação da animação, difícil de fazer manualmente
            .animateContentSize(
                // permite personalizar a animação
                animationSpec = spring(
                    //spring - propriedades físicas (amortecimento e rigidez) dependem deles para tornar as animações mais naturais
                    dampingRatio = Spring.DampingRatioMediumBouncy,
                    stiffness = Spring.StiffnessLow
                )
            )
    ) {
        // Coluna - pode ser configurada para exibir o conteúdo no centro da tela
        Column(
            //disposição dos elementos
            modifier = Modifier
                //faz com que o elemento preencha todo o espaço disponível, tornando-o flexível, eliminando os outros elementos inflexíveis
                .weight(1f) 
                //espaço ao redor da coluna
                .padding(12.dp)
        ) {
            Text(text = "Hello, ")
            Text(
                // composição Text define um novo TextStyle. Você pode criar seu próprio TextStyle ou extrair um estilo definido pelo tema usando MaterialTheme.typography, que é preferencial.
                // função copy - possibilita modificar um estilo predefinido
                text = name, style = MaterialTheme.typography.headlineMedium.copy(
                    fontWeight = FontWeight.ExtraBold
                )
            )
            // condição
            if (expanded) {
                Text(
                    text = ("Composem ipsum color sit lazy, " +
                        "padding theme elit, sed do bouncy. ").repeat(4),
                )
            }
        }
        //Substituindo o botão por um ícone
        IconButton(onClick = { expanded = !expanded }) {
            Icon(
                imageVector = if (expanded) Filled.ExpandLess else Filled.ExpandMore,
                contentDescription = if (expanded) {
                    stringResource(R.string.show_less)
                } else {
                    stringResource(R.string.show_more)
                }
            )
        }
    }
}

// Visualização no modo escuro
@Preview(
    showBackground = true,
    // mudando a visualização para emular a largura comum de um smartphone pequeno de 320 dp 
    widthDp = 320,
    // adiciona uma visualização no modo escuro
    uiMode = UI_MODE_NIGHT_YES,
    name = "DefaultPreviewDark"
)

// Visualização
@Preview(showBackground = true, widthDp = 320)
@Composable
fun DefaultPreview() {
    // maneira de definir o estilo de funções de composição
    BasicoJetpackComposeTheme {
        Greetings()
    }
}

// Visualização e Função OnboardingPreview
@Preview(showBackground = true, widthDp = 320, heightDp = 320)
@Composable
fun OnboardingPreview() {
    // maneira de definir o estilo de funções de composição
    BasicoJetpackComposeTheme {
        OnboardingScreen(onContinueClicked = {})
    }
}

// Visualização e função MyAppPreview
@Preview
@Composable
fun MyAppPreview() {
    BasicoJetpackComposeTheme {
        MyApp(Modifier.fillMaxSize())
    }
}