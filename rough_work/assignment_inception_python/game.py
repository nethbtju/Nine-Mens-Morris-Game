from player import Player


class GameEngine.GameEngine:
    "The main game engine."

    def __init__(self, players: list[Player]):
        self.players = players
        self.turn_number = 0
        self.loser = None

    def tick(self):
        "Process one turn in the game."

        player_number = self.turn_number % 2
        current_player = self.players[player_number]
        try:
            current_player.play_turn()
        except:
            self.loser = current_player

    def run(self):
        "Run the game."

        while not self.loser:
            self.tick()
