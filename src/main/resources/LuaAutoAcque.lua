--�жϸ��û��Ƿ���ɱ���������������������
local hasSecKill=redis.call('sismember',ARGV[1],KEYS[1])       
if hasSecKill ~=0 then
    return 0;
end

 --����������ʶ
--redis.call('set',KEYS[1],1);
--���ù���ʱ��
--redis.call('expire',KEYS[1],30000);

--check���
for goodsNum=2,#KEYS  do
    local goodsStock=redis.call('get',KEYS[goodsNum]);
    if goodsStock< ARGV[goodsNum] then
        return 2;
    end
end

--�ۿ��
for goodsNum=2,#KEYS do
    redis.call('DECRBY',KEYS[goodsNum],ARGV[goodsNum]);
end

-- ���������ɹ����û�
redis.call('sadd',ARGV[#ARGV],KEYS[1]);

return 1;
