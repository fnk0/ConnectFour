package main;

//======================================================================
// CS I : #  Program 10
// # Connect 4
// Semester : # (Fall 2013)
//
// # Marcus Andreo Gabilheri
// # 002
//
// Description: This program is a GUI implementation of the classig game
// Connect 4.
//======================================================================

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class ConnectFour extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;

	private static ArrayList<JPanel> gameSlots = new ArrayList<JPanel>();
	private static ArrayList<JButton> gameButtons = new ArrayList<JButton>();
	private static int[] numbersPressed = { 5, 5, 5, 5, 5, 5, 5 };
	private static int countTurns = 0;
	private static Container contentPane;
	private static JPanel gamePanel;
	private static JPanel northPanel;
	private static JPanel southPanel;
	private static JButton resetScores;
	private static JButton newGame;
	private static JLabel player1;
	private static JLabel player2;
	private static JLabel playerTurn;
	private static int playerTurnNumber;
	private static int[][] gameBoard = new int[6][7];
	private static int playerOneScore = 0;
	private static int playerTwoScore = 0;
	private static int gameMode = 0;
	private static int actionCounter = 0;
	private static String userDialog = "";
	private static String userDialog2;

	public ConnectFour() {
		super("Connect Four by Marcus Gabilheri");
		setSize(750, 600);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		
		userDialog = JOptionPane.showInputDialog("Player 1");
		userDialog2 = JOptionPane.showInputDialog("Player 2");

		gamePanel = new JPanel();
		northPanel = new JPanel();
		southPanel = new JPanel();
		northPanel.setLayout(new GridLayout(0, 5));
		gamePanel.setLayout(new GridLayout(6, 7));
		southPanel.setLayout(new GridLayout(0, 7));

		resetScores = new JButton("Reset Scores");
		newGame = new JButton("New Game");
		player1 = new JLabel(userDialog + " Score:  " + playerOneScore);
		player2 = new JLabel(userDialog2 + " 2 Score:  " + playerTwoScore);
		player1.setFont(new Font(null, Font.BOLD, 15));
		player2.setFont(new Font(null, Font.BOLD, 15));
		player1.setForeground(Color.YELLOW);
		player2.setForeground(Color.RED);
		northPanel.setBackground(Color.BLUE);

		playerTurnNumber = 1;
		playerTurn = new JLabel("Turn: " + userDialog);
		playerTurn.setFont(new Font(null, Font.BOLD, 15));
		playerTurn.setForeground(Color.YELLOW);
		resetScores.addActionListener(this);
		newGame.addActionListener(this);

		northPanel.add(newGame);
		northPanel.add(resetScores);

		ArrayList<JLabel> labels = new ArrayList<JLabel>();
		labels.add(player1);
		labels.add(player2);
		labels.add(playerTurn);

		for (JLabel x : labels) {
			northPanel.add(x);
			x.setBorder(BorderFactory.createLineBorder(Color.black));
		}

		for (int i = 0; i < 7; i++) {
			JButton myButton = new JButton("Drop");
			myButton.setFont(new Font(null, Font.BOLD, 25));
			myButton.addActionListener(this);
			myButton.setBorder(BorderFactory.createLineBorder(Color.black));
			gameButtons.add(myButton);
			southPanel.add(gameButtons.get(i));
		}

		contentPane = this.getContentPane();

		contentPane.add(northPanel, BorderLayout.NORTH);
		contentPane.add(gamePanel, BorderLayout.CENTER);
		contentPane.add(southPanel, BorderLayout.SOUTH);
		int count = 0;

		for (int row = 0; row < gameBoard.length; row++) {
			for (int col = 0; col < gameBoard[1].length; col++) {
				gameSlots.add(new DrawStuff(0));
				gamePanel.add(gameSlots.get(count));
				count++;
			}
		}
	}

	public static void main(String[] args) {

		ConnectFour myGame = new ConnectFour();
		myGame.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent event) {

		System.out.println("Action " + actionCounter + " has been performed!!");
		actionCounter++;
		Object source = event.getSource();
		try {
			if (gameMode == 0) {
				for (int i = 0; i < gameButtons.size(); i++) {
					if (source == gameButtons.get(i)) {
						int y = numbersPressed[i];
						int x = i + (7 * y);

						if (countTurns % 2 == 0) {
							((DrawStuff) gameSlots.get(x)).setType(1);
							repaint();
							gameBoard[y][i] = 1;
							if (checkForWin(y, i, 1)) {
								playerOneScore++;
								player1.setText(userDialog + " Score: "
										+ playerOneScore);
							}
							playerTurn.setForeground(Color.RED);
							playerTurnNumber = 2;
						} else {
							((DrawStuff) gameSlots.get(x)).setType(2);
							repaint();
							gameBoard[y][i] = 2;
							if (checkForWin(y, i, 2)) {
								playerTwoScore++;
								player2.setText(userDialog2 + " Score: "
										+ playerTwoScore);
							}
							playerTurnNumber = 1;
							playerTurn.setForeground(Color.YELLOW);
						}
						countTurns++;
						numbersPressed[i] = y - 1;
						if (playerTurnNumber == 1) {
							playerTurn.setText("Turn: " + userDialog);
						} else {
							playerTurn.setText("Turn: " + userDialog2);
						}
						repaint();
					}
				}
			}
		} catch (Exception e) {
		}

		if (source == resetScores) {
			playerOneScore = 0;
			playerTwoScore = 0;

			player1.setText(userDialog + " Score: " + playerOneScore);
			player2.setText(userDialog2 + " Score: " + playerOneScore);

		} else if (source == newGame) {
			for (JPanel x : gameSlots) {
				((DrawStuff) x).setType(0);
			}
			for (int i = 0; i < numbersPressed.length; i++) {
				numbersPressed[i] = 5;
			}
			for (int r = 0; r < gameBoard.length; r++) {
				for (int c = 0; c < gameBoard[r].length; c++) {
					gameBoard[r][c] = 0;
				}
			}

			if (countTurns % 2 != 0) {
				countTurns = 1;
				playerTurn.setText("Turn: " + userDialog2);
				playerTurn.setForeground(Color.RED);
				playerTurnNumber = 2;
			} else {
				playerTurn.setText("Turn: " + userDialog);
				playerTurn.setForeground(Color.YELLOW);
				countTurns = 0;
				playerTurnNumber = 1;
			}
			gameMode = 0;
			invalidate();
			validate();
			repaint();
		}
	}

	public boolean checkForWin(int x, int y, int p) {
		String player = "";
		if (p == 1) {
			player = userDialog;
		} else {
			player = userDialog2;
		}

		if (checkDiagonal(x, y, p)) {
			JOptionPane.showConfirmDialog(gamePanel, player
					+ " wins Diagonally 1", "GAME OVER!",
					JOptionPane.CLOSED_OPTION, JOptionPane.INFORMATION_MESSAGE);
			gameMode = 1;
			return true;
		}
		if (checkAnotherDiagonal(x, y, p)) {
			JOptionPane.showConfirmDialog(gamePanel, player
					+ " wins Diagonally 2", "GAME OVER!",
					JOptionPane.CLOSED_OPTION, JOptionPane.INFORMATION_MESSAGE);
			gameMode = 1;
			return true;
		}

		if (checkHorizontal(x, y, p)) {
			JOptionPane.showConfirmDialog(gamePanel, player
					+ " wins Horizontally!", "GAME OVER!",
					JOptionPane.CLOSED_OPTION, JOptionPane.INFORMATION_MESSAGE);
			gameMode = 1;
			return true;
		}
		if (checkVertical(x, y, p)) {
			JOptionPane.showConfirmDialog(gamePanel, player
					+ " wins Vertically!", "GAME OVER",
					JOptionPane.CLOSED_OPTION, JOptionPane.INFORMATION_MESSAGE);
			gameMode = 1;
			return true;
		}

		return false;
	}

	public boolean checkDiagonal(int x, int y, int p) {
		int counter = 0;
		int k = x - 1;
		int i = y + 1;
		int n = x + 1;
		int j = y - 1;
		try {
			if (x == 5) {
				for (; x > -1 && y < 7; x--, y++) {
					if (gameBoard[x][y] == p) {
						counter++;
					} else {
						break;
					}
				}
				if (counter >= 4) {
					return true;
				}
			} else {
				counter = 1;
				for (; k > -1 && i < 7; k--, i++) {
					if (gameBoard[k][i] == p) {
						counter++;
					} else {
						break;
					}
				}

				for (; n < 6 && j > -1; n++, j--) {
					if (gameBoard[n][j] == p) {
						counter++;
					} else {
						break;
					}
				}

				if (counter >= 4) {
					return true;
				}
			}
		} catch (Exception e) {
		}
		return false;
	} // End of checkDiagonal

	public boolean checkAnotherDiagonal(int x, int y, int p) {
		int counter = 0;
		int k = x - 1;
		int i = y - 1;
		int n = x + 1;
		int j = y + 1;
		try {
			if (x == 5) {
				for (; x > -1 && y > -1; x--, y--) {
					if (gameBoard[x][y] == p) {
						counter++;
					} else {
						break;
					}
				}
				if (counter >= 4) {
					return true;
				}

			} else {
				counter = 1;
				for (; k > -1 && i > -1; k--, y--) {
					if (gameBoard[k][i] == p) {
						counter++;
					} else {
						break;
					}
				}
				for (; n < 6 && j < 7; n++, j++) {
					if (gameBoard[n][j] == p) {
						counter++;
					} else {
						break;
					}
				}
				if (counter >= 4) {
					return true;
				}
			}
		} catch (Exception e) {
		}
		return false;
	} // End of checkAnotherDiagonal()

	public boolean checkVertical(int x, int y, int p) {
		int count = 0;
		try {
			for (int i = 0; i <= 6; i++) {
				if (gameBoard[x + i][y] == p || gameBoard[x - i][y] == p) {
					count++;
				} else {
					break;
				}
			}

		} catch (Exception e) {
		}

		if (count == 4) {
			return true;
		}

		return false;

	} // End of checkVertical()

	public boolean checkHorizontal(int x, int y, int p) {
		int counter = 1;
		try {

			for (int i = y + 1; i < 7; i++) {

				if (gameBoard[x][i] == p) {
					counter++;
				} else {
					break;
				}
			}

			for (int j = y - 1; j > -1; j--) {
				if (gameBoard[x][j] == p) {
					counter++;
				} else if (gameBoard[j][y] != p) {
					break;
				}
			}

			if (counter >= 4) {
				return true;
			}

		} catch (Exception e) {
		}
		return false;
	} // End of checkHorizontal()

} // End of ConnectFour
