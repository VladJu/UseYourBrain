package com.example.useyourbrain.domain.usecases

import com.example.useyourbrain.domain.entity.GameSettings
import com.example.useyourbrain.domain.entity.Level
import com.example.useyourbrain.domain.repository.GameRepository


// load setting game in dependency to level
class GetGameSettingUseCase(
    private val repository: GameRepository
) {

    operator fun invoke(level: Level): GameSettings {
        return repository.getGameSettings(level)
    }

}