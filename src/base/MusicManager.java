package base;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

/**
 * 播放音乐
 *
 */
public class MusicManager {
	private static ExecutorService bgmService = Executors.newSingleThreadExecutor();
	private static boolean needPlayBgm;
	
	private static ExecutorService effService = Executors.newSingleThreadExecutor();
	
	/**
	 * 点击
	 * @throws IOException 
	 * @throws FileNotFoundException 
	 * @throws JavaLayerException 
	 */
	public static void playClick(){
		playerEff("click.mp3");	
	}

	private static void playerEff(String fileName){
		effService.execute(new Runnable() {
			@Override
			public void run() {
				try(BufferedInputStream buffer = new BufferedInputStream(MusicManager.class.getResourceAsStream("/res/music/" + fileName))){
					Player effPlayer = new Player(buffer);
					effPlayer.play();
					effPlayer.close();
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				} catch (JavaLayerException e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	/**
	 * 输入
	 * @throws FileNotFoundException
	 * @throws IOException
	 * @throws JavaLayerException
	 */
	public static void playEnter(){
		playerEff("enter.mp3");	
	}
	
	/**
	 * 快没时间
	 */
	public static void playTimeout(){
		playerEff("timeout.mp3");	
	}
	
	/**
	 * 胜利
	 * @throws FileNotFoundException
	 * @throws IOException
	 * @throws JavaLayerException
	 */
	public static void playWin(){
		playeShortMusicWithoutBgm("win.mp3");	
	}
	
	
	/**
	 * 失败
	 * @throws FileNotFoundException
	 * @throws IOException
	 * @throws JavaLayerException
	 */
	public static void playLose(){
		playeShortMusicWithoutBgmAndWait("lose.mp3");	
	}
	
	private static void playeShortMusicWithoutBgmAndWait(String fileName){
		stopBgm();
		Future<?> future = bgmService.submit(new Runnable() {
			@Override
			public void run() {
				try(BufferedInputStream buffer = new BufferedInputStream(MusicManager.class.getResourceAsStream("/res/music/" + fileName))){
					Player effPlayer = new Player(buffer);
					effPlayer.play();
					effPlayer.close();
					
					playBgm();
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				} catch (JavaLayerException e) {
					e.printStackTrace();
				}
			}
		});
		
		//阻塞
		try {
			future.get(3, TimeUnit.SECONDS);
		} catch (InterruptedException | ExecutionException e1) {
			e1.printStackTrace();
		} catch (TimeoutException e) {
		}
	}
	
	private static void playeShortMusicWithoutBgm(String fileName){
		stopBgm();
		bgmService.execute(new Runnable() {
			@Override
			public void run() {
				try(BufferedInputStream buffer = new BufferedInputStream(MusicManager.class.getResourceAsStream("/res/music/" + fileName))){
					Player effPlayer = new Player(buffer);
					effPlayer.play();
					effPlayer.close();
					playBgm();
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				} catch (JavaLayerException e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	/**
	 * 背景音乐
	 * 
	 * @throws JavaLayerException
	 * @throws IOException
	 * @throws FileNotFoundException
	 */
	public static void playBgm(){
		needPlayBgm = true;
		bgmService.execute(new Runnable() {
			@Override
			public void run() {
				try(BufferedInputStream buffer = new BufferedInputStream(MusicManager.class.getResourceAsStream("/res/music/bgm.mp3"))){
					Player bgmPlayer = new Player(buffer);
					while(needPlayBgm){
						bgmPlayer.play(20);
					}
					bgmPlayer.close();
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				} catch (JavaLayerException e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	/**
	 * 停止播放背景音乐
	 */
	public static void stopBgm(){
		needPlayBgm = false;
	}
	
	
	/**
	 * 释放所有资源
	 */
	public static void dispose(){
		bgmService.shutdownNow();
		effService.shutdown();
	}
}
