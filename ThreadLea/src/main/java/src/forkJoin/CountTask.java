package src.forkJoin;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;
import java.util.concurrent.RecursiveTask;

public class CountTask extends RecursiveTask<Integer> {
    private static final int THRESHOLD = 4;//阈值
    private int start;
    private int end;

    public CountTask(int start, int end) {
        this.start = start;
        this.end = end;
    }

    @Override
    protected Integer compute() {
        int sum = 0;
        boolean canCompute = (end - start) <= THRESHOLD;
        if (canCompute) {
            for (int i = start; i <= end; i++) {
                sum += i;
            }
        } else {
            //任务大于阈值就分裂成两个子任务计算
            int middle = (start + end) / 2;
            CountTask leftTash = new CountTask(start, middle);
            CountTask rightTask = new CountTask(middle + 1, end);
            //执行子任务
            leftTash.fork();
            rightTask.fork();
            //等待子任务完成，并得到结果
            int leftResult = leftTash.join();
            int rightRusult = rightTask.join();
            //合并自认的值
            sum = leftResult + rightRusult;
        }
        return sum;
    }


    public static void main(String[] args) {
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        CountTask task = new CountTask(1, 100);
        //执行任务
        Future<Integer> result = forkJoinPool.submit(task);
        try {
            System.out.println(result.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
        }
    }
}
