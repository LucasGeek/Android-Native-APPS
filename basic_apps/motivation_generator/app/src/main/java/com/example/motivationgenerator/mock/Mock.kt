package com.example.motivationgenerator.mock

import com.example.motivationgenerator.util.MotivationConstants
import kotlin.random.Random

class Phrase(val description: String, val category: Int)

fun Int.random(): Int = Random.nextInt(this)

class Mock {

    private val ALL = MotivationConstants.PHRASE_FILTER.ALL
    private val MORNING = MotivationConstants.PHRASE_FILTER.MORNING
    private val HAPPY = MotivationConstants.PHRASE_FILTER.HAPPY

    private val mListPhrases: List<Phrase> = listOf(
        Phrase("Não sabendo que era impossível, foi lá e fez.", HAPPY),
        Phrase("Você não é derrotado quando perde, você é derrotado quando desiste!", HAPPY),
        Phrase("Quando está mais escuro, vemos mais estrelas!", HAPPY),
        Phrase("Insanidade é fazer sempre a mesma coisa e esperar um resultado diferente.", HAPPY),
        Phrase("Não pare quando estiver cansado, pare quando tiver terminado.", HAPPY),
        Phrase("O que você pode fazer agora que tem o maior impacto sobre o seu sucesso?", HAPPY),
        Phrase("Felicidade tem nome: Deus, família e amigos.", HAPPY),
        Phrase("Não importa o que você decidiu. O que importa é que isso te faça feliz.", HAPPY),
        Phrase("Ser feliz não é viver apenas momentos de alegria. É ter coragem de enfrentar os momentos de tristeza e sabedoria para transformar os problemas em aprendizado.", HAPPY),
        Phrase("Nem todos os anjos tem asas, às vezes eles têm apenas o dom de te fazer sorrir.", HAPPY),
        Phrase("A vida me ensinou que chorar alivia, mas sorrir torna tudo mais bonito.", HAPPY),
        Phrase("Chique é ser feliz. Elegante é ser honesto. Bonito é ser caridoso. Sábio é saber ser grato. O resto é inversão de valores.", HAPPY),
        Phrase("Corra o risco. Se der certo, felicidade. Se não, sabedoria.", HAPPY),
        Phrase("Prefira o sorriso, faz bem a você e aos que estão ao seu redor. Dê risada de tudo, de si mesmo. Não adie alegrias. Seja feliz hoje!", HAPPY),
        Phrase("Tomara que a felicidade te pegue de jeito, e não te solte nunca mais... Vou achar bem feito porque você vai ganhar o que você merece: ser feliz!", HAPPY),
        Phrase("Que o vento leve, que a chuva lave, que a alma brilhe, que o coração acalme, que a harmonia se instale e a felicidade permaneça.", HAPPY),
        Phrase("A amizade não se compra, se encontra. A felicidade não se encontra, se sente. O amor não se sente, se vive.", HAPPY),
        Phrase("Que a vontade de ser feliz seja sempre maior que o medo de se machucar.", HAPPY),
        Phrase("Aí vem a vida e te vira do avesso só para provar que a felicidade vem de dentro pra fora.", HAPPY),
        Phrase("Faça mais do que te faz feliz.", HAPPY),
        Phrase("Sorrir não mata. Viver não dói. Abraçar não arde. Beijar não fere. Rir não machuca. Ou seja, você não tem motivos para não tentar ser feliz.", HAPPY),

        Phrase("Não importa a cor do céu. Quem faz o dia bonito é você.", MORNING),
        Phrase("Bom dia! Que seu dia seja igual a vontade de Deus: bom, perfeito e agradável.", MORNING),
        Phrase("Sorria! Deus acaba de te dar um novo dia e coisas extraordinárias podem acontecer se você crer!", MORNING),
        Phrase("Um pequeno pensamento positivo pela manhã pode mudar todo o seu dia.", MORNING),
        Phrase("Que o dia seja leve, que a tristeza seja breve e que o dia seja feliz. Bom dia!", MORNING),
        Phrase("Pra hoje: sorrisos bobos, uma mente tranquila e um coração cheio de paz.", MORNING),
        Phrase("Que o dia comece bem e termine ainda melhor.", MORNING),
        Phrase("Independente do que estiver sentindo, levante-se, vista-se e saia para brilhar.", MORNING),
        Phrase("A cada nova manhã, nasce junto uma nova chance. Bom dia!", MORNING),
        Phrase("Bom dia! Comece o dia sorrindo. Afinal, coisa boa atrai coisa boa.", MORNING),
        Phrase("Sempre que o sol nasce, você tem uma nova oportunidade de ser feliz. Bom dia!", MORNING),
        Phrase("Que o amor seja a melhor forma de começar e terminar o dia.", MORNING),
        Phrase("Que esse dia seja cheio de energia positiva, amém.", MORNING),
        Phrase("Hoje eu acordei tão linda que quando fui bocejar, miei.", MORNING),
        Phrase("Tenha um bom dia, uma semana fantástica e uma vida maravilhosa.", MORNING),
        Phrase("A melhor maneira de prever o futuro é inventá-lo.", MORNING),
        Phrase("Você perde todas as chances que você não aproveita.", MORNING),
        Phrase("Fracasso é o condimento que dá sabor ao sucesso.", MORNING),
        Phrase("Enquanto não estivermos comprometidos, haverá hesitação!", MORNING),
        Phrase("Se você não sabe onde quer ir, qualquer caminho serve.", MORNING),
        Phrase("Se você acredita, faz toda a diferença.", MORNING),
        Phrase("Riscos devem ser corridos, porque o maior perigo é não arriscar nada!", MORNING)
    )

    fun getPhrase(value: Int): String {
        val filtered = mListPhrases.filter { it -> (it.category == value || value == ALL) }
        val rand = (filtered.size).random()

        return filtered[rand].description
    }
}