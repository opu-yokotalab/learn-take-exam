/** * CountDown.java * �J�E���g�_�E��������N���X * * Created: Wed Sep 03 01:05:33 2003 * * @author * @version 1.0 */
public class Timer {
	/**�J�E���g(�C���X�^���X�ϐ��Ȃ̂�_������)*/
	private int count_;
	/** * �J�E���g�C���X�^���X�����o���D������ * count���𓾂�D * @param count an int value */
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