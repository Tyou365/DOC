            for (int i = 0; i < callables.size(); i++) {
                competionService.take();
            }
            

            for (int i = 0; i < callables.size(); i++) {
                competionService.take().get();
            }
            会出现阻塞，等待线程的结果。
            
            1、competionService.take();-->主线程需要等待。
            2、competionService.take().get();-->主线程需要等待。
            3、不使用competionService.take()。-->主线程继续，不需要等待。
            3种情况。
            
