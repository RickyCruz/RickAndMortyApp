package com.example.rickmorty.components.episode

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.rickmorty.components.shared.CharacterDataComponent
import com.example.rickmorty.components.shared.DataCharacter
import com.example.rickmorty.network.domain.Episode
import com.example.rickmorty.ui.theme.TextPrimary

@Composable
fun EpisodeRowComponent(episode: Episode) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        CharacterDataComponent(
            data = DataCharacter(
                title = "Episode",
                description = episode.episodeNumber.toString()
            )
        )
        Spacer(modifier = Modifier.width(64.dp))
        Column {
            Text(
                text = episode.name,
                fontSize = 24.sp,
                color = TextPrimary,
                textAlign = TextAlign.End,
                modifier = Modifier.fillMaxWidth()
            )
            Text(
                text = episode.airDate,
                fontSize = 16.sp,
                color = TextPrimary,
                fontStyle = FontStyle.Italic,
                textAlign = TextAlign.End,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Preview
@Composable
private fun EpisodeRowComponentPreview() {
    val episode = Episode(
        id = 10,
        name = "Close Rick-counters of the Rick Kind",
        seasonNumber = 1,
        episodeNumber = 10,
        airDate = "April 7, 2014",
        characterIdsInEpisode = listOf(1, 2, 3, 4, 5)
    )

    EpisodeRowComponent(episode = episode)
}