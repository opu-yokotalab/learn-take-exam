/** * CountDown.java * カウントダウンをするクラス * * Created: Wed Sep 03 01:05:33 2003 * * @author * @version 1.0 */
public class Timer {
	/**カウント(インスタンス変数なので_をつける)*/
	private int count_;
	/** * カウントインスタンスを作り出す．引数に * count数を得る． * @param count an int value */
	public Timer(int count) {
		count_ = count;
		} 
	// CountDown constructor 
	public void countdown(){
		if(count_ > 0){
			count_--;
			}
		System.out.println("countdown : "+count_);
		}
	
	
	void main(){
		Timer t = new Timer(10);
	}
} // CountDown 